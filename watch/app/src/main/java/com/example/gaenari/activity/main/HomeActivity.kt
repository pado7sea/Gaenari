package com.example.gaenari.activity.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.gaenari.R
import com.example.gaenari.StepCounterViewModel
import com.example.gaenari.dto.response.ApiResponseListDto
import com.example.gaenari.dto.response.FavoriteResponseDto
import com.example.gaenari.model.SharedViewModel
import com.example.gaenari.service.LocationService
import com.example.gaenari.util.AccessToken
import com.example.gaenari.util.PreferencesUtil
import com.example.gaenari.util.Retrofit
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private var prefs: SharedPreferences? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: MyFragmentStateAdapter
    private lateinit var viewModel: StepCounterViewModel

    //즐겨찾기 데이터를 여기에 담아서 프래그먼트에서 보여줄예정
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        setContentView(R.layout.activity_home)
        prefs = PreferencesUtil.getEncryptedSharedPreferences(applicationContext)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // 프래그먼트 어댑터 및 ViewPager2 설정
        adapter = MyFragmentStateAdapter(this)  // 어댑터 설정
        viewPager.adapter = adapter  // 어댑터 연결

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Third"
                1 -> tab.text = "First"
                2 -> tab.text = "Second"
                3 -> tab.text = "Third"
                4 -> tab.text = "First"
            }
        }.attach()  // 탭과 ViewPager2 연동

        // 두 번째 페이지로 초기 설정
        viewPager.setCurrentItem(2, false)

        // 원형 스크롤 구현
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val currentItem = viewPager.currentItem
                    val itemCount = viewPager.adapter?.itemCount ?: 0

                    if (currentItem == itemCount - 1) {
                        viewPager.setCurrentItem(1, false)  // 마지막에서 첫 번째로 이동
                    }

                    if (currentItem == 0) {
                        viewPager.setCurrentItem(itemCount - 2, false)  // 첫 번째에서 마지막으로 이동
                    }
                }
            }
        })
        getFavoriteProgram();

        this.onBackPressedDispatcher.addCallback(this, callback)
        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(StepCounterViewModel::class.java)

        setupLocationService()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            Log.d("Check", "On BackPressedCallback : position(${viewPager.currentItem})")

            if (viewPager.currentItem == 2)
                finish()
            else
                viewPager.setCurrentItem(2, true)
        }
    }

    private fun setupLocationService() {
        val intent = Intent(this@HomeActivity, LocationService::class.java)
        startForegroundService(intent)
    }

    private fun getFavoriteProgram() {
        if (AccessToken.getInstance().accessToken == null)
            AccessToken.getInstance().accessToken = prefs!!.getString("accessToken", null)

        Log.d("Check", "Access Token : " + AccessToken.getInstance().accessToken)

        val call = Retrofit.getApiService()
            .getFavoriteProgram(AccessToken.getInstance().accessToken)


        call.enqueue(object : Callback<ApiResponseListDto<FavoriteResponseDto?>> {
            override fun onResponse(
                call: Call<ApiResponseListDto<FavoriteResponseDto?>>,
                response: Response<ApiResponseListDto<FavoriteResponseDto?>>
            ) {
                Log.d("Response", "Favorite Response : $response")
                Log.d("Check", "Favorite program : " + response.body()?.data)
                if (response.body()?.status == "ERROR") {
                    Toast.makeText(this@HomeActivity, "즐겨찾기 목록 조회 실패.", Toast.LENGTH_SHORT).show()
                } else {
                    val dataList: List<FavoriteResponseDto?> = response.body()?.data ?: emptyList()

                    // ViewModel에 데이터 전달
                    sharedViewModel.setFavoritePrograms(dataList)
                }
            }

            override fun onFailure(
                call: Call<ApiResponseListDto<FavoriteResponseDto?>>,
                t: Throwable
            ) {
                Toast.makeText(this@HomeActivity, "API 연결 실패.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        viewPager.setCurrentItem(2, false)
        super.onResume()
    }
}
