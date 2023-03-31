package com.example.aop.part2.imagedisplay.views
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part2.imagedisplay.databinding.ActivityMainBinding
import com.example.aop.part2.imagedisplay.viewmodels.PhotoViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val photoAdapter: PhotoAdapter by lazy { PhotoAdapter() }

    private val photoViewModel: PhotoViewModel by lazy { PhotoViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // PhotoItemList 옵저빙
        photoViewModel.photoItemList.observe(this) {
            photoAdapter.addList(it)
        }

        // PhotoSearch 옵저빙
        photoViewModel.searchTerm.observe(this) {
            try {
                photoAdapter.addSearchedList(photoViewModel.photoItemList.value!!, it)
            } catch (e: NullPointerException) { Log.d("JB", "Exception : $e") }
        }

        photoViewModel.getRandomPhotos(10)


        // ScrollEvent 최하단 감지
        val onScrollListener = object:RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding.recyclerViewPhoto.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    when(photoViewModel.searchTerm.value) {
                        null -> {photoViewModel.getRandomPhotos(10)}
                        else -> {photoViewModel.getSearchPhotos(binding.editText.text.toString())}
                    }
                }
            }
        }
        binding.recyclerViewPhoto.addOnScrollListener(onScrollListener)


        searchPhotos()
        initAdapter()

    }

    // Photo Search
    private fun searchPhotos() {
        binding.editText.setOnEditorActionListener { editText, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) { // SEARCH 버튼 눌렀을 때만 처리
                currentFocus?.let { view ->
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
                    view.clearFocus()   // search 가 끝나면 커서 깜빡거림 제거
                }
                // editText 의 값을 전달, 데이터가 변경되면서 갱신됨
                photoViewModel.getSearchPhotos(editText.text.toString())
            }
            true
        }
    }



    private fun initAdapter() {
        binding.recyclerViewPhoto.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}