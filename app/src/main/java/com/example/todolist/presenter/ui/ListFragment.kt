package com.example.todolist.presenter.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.adapters.ToDoItemsListAdapter
import com.example.todolist.presenter.callbacks.RecyclerToEditCallback
import com.example.todolist.presenter.delegates.ImportantDelegate
import com.example.todolist.presenter.delegates.ListRecyclerDelegate
import com.example.todolist.presenter.delegates.NonImportantDelegate
import com.example.todolist.data.network.TokenRepositoryImpl
import com.example.todolist.domain.models.OauthToken
import com.example.todolist.utils.SwipeGesture
import com.example.todolist.presenter.viewModel.DataViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class ListFragment : Fragment() {

    private var tokenObj: OauthToken = OauthToken("")
    private var expiredDate : Long = 0
    private var isLoggedIn = false
    private val activityViewModel: DataViewModel by activityViewModels()
    @Inject
    lateinit var tokenManager: TokenRepositoryImpl
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var navController: NavController
    lateinit var adapter: ToDoItemsListAdapter
    lateinit var addButton: FloatingActionButton
    lateinit var logInButton: Button
    lateinit var setCompleteFun: (position: Int) -> Unit
    lateinit var deleteFun: (position: Int) -> Unit
    lateinit var isCompleteCheck: (position: Int) -> Boolean
    lateinit var swipeGesture: SwipeGesture

    private var yandexAuthToken: YandexAuthToken = YandexAuthToken("", 1)
    private lateinit var authOptions: YandexAuthOptions
    private lateinit var sdk: YandexAuthSdk
    private lateinit var loginOptionsBuilder: YandexAuthLoginOptions.Builder
    private lateinit var authIntent: Intent

    private lateinit var  adapterDelegates :List<ListRecyclerDelegate>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        Log.d("lifecycle", "onCreateView")
        logInButton = view.findViewById(R.id.logIn_Button)
        checkTokenDataStore()
        authOptions = YandexAuthOptions(requireContext(), true)
        sdk = YandexAuthSdk(requireContext(), authOptions)
        loginOptionsBuilder = YandexAuthLoginOptions.Builder()
        authIntent = sdk.createLoginIntent(loginOptionsBuilder.build())
        adapterDelegates = listOf(
            NonImportantDelegate(requireContext()),
            ImportantDelegate(requireContext())
        )
        recyclerView = view.findViewById(R.id.to_do_items_list)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        recyclerView.layoutManager = layoutManager
        navController = findNavController()
        adapter = ToDoItemsListAdapter(RecyclerToEditCallback(navController), adapterDelegates)
        activityViewModel.getToDoItems()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.itemsList.collect{
                    adapter.differ.submitList(it)
                }
            }
        }

        recyclerView.adapter = adapter

        addButton = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_toDoItemFragment)
        }


        setCompleteFun = { position ->
            val item = adapter.differ.currentList[position]
            Log.d("Item_text", item.text)
            if(!item.isComplete){
                item.isComplete = true
                activityViewModel.addToDoItem(item)
            }
            adapter.notifyItemChanged(position)
        }
        deleteFun = { position ->
            val item = adapter.differ.currentList[position]
            Log.d("Item_text", item.text)
            activityViewModel.deleteItem(item)
            showDeleteSnackBar(view, item)
        }

        isCompleteCheck = { position ->
            val item = adapter.differ.currentList[position]
            item.isComplete
        }


        swipeGesture = SwipeGesture(requireContext())
        swipeGesture.rightSwipeListener = setCompleteFun
        swipeGesture.leftSwipeListener = deleteFun
        swipeGesture.isCompleteListener = isCompleteCheck

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }

    override fun onPause() {
        super.onPause()
        saveAuthInf()
        Log.d("lifecycle", "onPause")
    }

    private fun showDeleteSnackBar(view: View, item: ToDoItemEntity){
        val snackbar = Snackbar.make(view,
            requireContext().getString(R.string.delete_snackbar),
            Snackbar.LENGTH_LONG)
            .apply {
                setAction(requireContext().getString(R.string.cancel)){
                    activityViewModel.addToDoItem(item)
                }
                setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }
        val snackbarView = snackbar.view
        val snackbarTextView : TextView
        = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
        snackbarTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.show()
    }

    private val getTokenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            yandexAuthToken = sdk.extractToken(result.resultCode, result.data)!!
            tokenObj.token = yandexAuthToken.value
//            expiredDate = yandexAuthToken.expiresIn()
            setLoggedInButtonState()
            saveAuthInf()
            Log.d("token", yandexAuthToken.toString())
        }


    private fun logOut(){
        setLoggedOutButtonState()
    }
    private fun saveAuthInf(){
        if (isLoggedIn)
            activityViewModel.saveOauthToken(tokenObj)
        else
            activityViewModel.deleteOauthToken()
    }
    private fun setLoggedInButtonState(){
        logInButton.setText(R.string.logOut)
        logInButton.setOnClickListener{logOut()}
        isLoggedIn = true
    }
    private fun setLoggedOutButtonState(){
        tokenObj = OauthToken("")
        expiredDate = 0
        logInButton.setText(R.string.logIn)
        logInButton.setOnClickListener{
            getTokenLauncher.launch(authIntent)
        }
        isLoggedIn = false
    }

    private fun checkTokenDataStore(){
        activityViewModel.getOAuthToken()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.token.collect{
                    tokenObj = it
                    withContext(Dispatchers.Main) {
                        if (tokenObj.token.isBlank()) {
                            setLoggedOutButtonState()
                            Log.d("networkList", "is blank")
                        } else {
                            setLoggedInButtonState()
                            Log.d("networkList", "is not blank")
                        }
                    }
                }
            }
        }
    }

}


