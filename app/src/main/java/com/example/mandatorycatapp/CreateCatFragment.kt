package com.example.mandatorycatapp

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatorycatapp.databinding.FragmentCreateCatBinding
import com.example.mandatorycatapp.models.AuthentificationLoginViewModel
import com.example.mandatorycatapp.models.Cat
import com.example.mandatorycatapp.models.CatsViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateCatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCreateCatBinding? = null
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthentificationLoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateCat.setOnClickListener {
            // TODO: error handling all over the place
            val catName: String = binding.catNameInput.text.toString().trim()
            val catDescription: String = binding.catDescriptionInput.text.toString().trim()
            val catPlace: String = binding.catPlaceInput.text.toString().trim()
            val catReward: Int = binding.catRewardInput.text.toString().trim().toInt()
            val catUserID: String = authenticationViewModel.userMutableLiveData.value?.email.toString()

            //val calendar: Calendar = Calendar.getInstance()
            //calendar.set(binding.catDateInput.year, binding.catDateInput.month, binding.catDateInput.dayOfMonth)
            //val catDate = calendar.timeInMillis / 1000
            val catDate = binding.catDateInput.text.toString().trim().toLong()

            val catPictureURL: String = binding.catPictureUrlInput.text.toString().trim()

            val lostCat = Cat(0, catName, catDescription, catPlace, catReward, catUserID, catDate, catPictureURL)

            catsViewModel.add(lostCat)
            //snackbar
            val snack = Snackbar.make(it, "Cat created", Snackbar.LENGTH_SHORT)
            snack.show()
            findNavController().popBackStack()

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateCatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateCatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}