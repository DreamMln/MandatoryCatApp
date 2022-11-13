package com.example.mandatorycatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatorycatapp.databinding.FragmentSecondBinding
import com.example.mandatorycatapp.models.AuthentificationLoginViewModel
import com.example.mandatorycatapp.models.CatsViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authentificationViewModel: AuthentificationLoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val cat = catsViewModel[position]
        if (cat == null) {
            binding.textviewMessage.text = "The cat doesn't excist!"
            return
        }
        binding.catObj.setText(cat.toLongString())

        if ( authentificationViewModel.userMutableLiveData.value?.email == cat.userId){
            binding.buttonDelete.visibility = View.VISIBLE
        }
        //delete button
        binding.buttonDelete.setOnClickListener{
            catsViewModel.delete(cat.id)
            findNavController().popBackStack()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}