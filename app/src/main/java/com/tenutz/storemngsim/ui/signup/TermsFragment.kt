package com.tenutz.storemngsim.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tenutz.storemngsim.databinding.FragmentTermsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.utils.ext.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsFragment: BaseFragment() {

    private var _binding: FragmentTermsBinding? = null
    val binding: FragmentTermsBinding get() = _binding!!

    val vm: TermsViewModel by viewModels()

    val args: TermsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.termsDetails(args.termsCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTermsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        binding.textTermsFtitle.text = args.title
        binding.btnTermsAgree.visibility = if(args.readOnly) View.GONE else View.VISIBLE
    }

    private fun observeData() {
        vm.termsDetails.observe(viewLifecycleOwner) {
            binding.textTermsMainContent.text =
                it.content?.let { it1 ->
                    HtmlCompat.fromHtml(
                        it1,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
        }
    }

    private fun setOnClickListeners() {
        binding.imageTermsCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnTermsAgree.setOnClickListener {
            setNavigationResult(args.termsCode to false, "agree")
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}