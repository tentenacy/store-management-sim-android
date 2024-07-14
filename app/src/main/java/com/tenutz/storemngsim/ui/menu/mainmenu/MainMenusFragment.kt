package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.generated.callback.OnClickListener
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainMenusFragment: Fragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentMainMenusBinding? = null
    val binding: FragmentMainMenusBinding get() = _binding!!

    lateinit var args: MainMenusNavArgs

    val vm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainMenusAdapter by lazy {
        MainMenusAdapter(
            onClickListener = { id, item ->
                when(id) {
                    R.id.constraint_imain_menus_container -> {
                        Logger.i((item as MainMenusResponse.MainMenu).toString())
                    }
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().apply {
            args = MainMenusNavArgs(
                getString("mainCategoryCode")!!,
                getString("middleCategoryCode")!!,
                getString("subCategoryCode")!!,
            )
        }

        Logger.i(args.toString())

        vm.mainMenus(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.mainMenus.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainMenus)
        }
    }

    private fun initViews() {
        binding.recyclerMainMenus.adapter = adapter
        editTextObservable(binding.editMainMenusSearch)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.mainMenus(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode, it)
            }
            .addTo(disposable)
    }

    private fun setOnClickListeners() {
        binding.textMainMenusEdit.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenusEditFragment())
        }
        binding.fabMainMenusAdd.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuAddFragment(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode))
        }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
        _binding = null
    }
}