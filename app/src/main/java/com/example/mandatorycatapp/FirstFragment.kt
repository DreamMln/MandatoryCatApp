package com.example.mandatorycatapp

import android.R
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mandatorycatapp.databinding.FragmentFirstBinding
import com.example.mandatorycatapp.models.AuthentificationLoginViewModel
import com.example.mandatorycatapp.models.CatsViewModel
import com.example.mandatorycatapp.models.MyAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //indsætter safeargs
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authentificationViewModel: AuthentificationLoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observe logout
        binding.textviewMessage.text =
            "Logged in: " + authentificationViewModel.userMutableLiveData.value
        authentificationViewModel.userMutableLiveData.observe(viewLifecycleOwner)
        { user ->
            if (user == null)
                binding.textviewMessage.text = "No one is logged in!"
            else
                binding.textviewMessage.text = "Logged in as user: " + user.email
        }

        //firebase, when user is logged in
        catsViewModel.catsLiveData.observe(viewLifecycleOwner)
        { cats ->
            //Log.d("APPLE", "observer $books")
            //binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility =
                if (cats == null)
                    View.GONE else View.VISIBLE
            if (cats != null) {
                val adapter = MyAdapter(cats) { position ->
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter

            }
        }

        catsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewError.text = errorMessage
        }
        //?
        catsViewModel.reload()

        //login a cat button
        /*binding.logonButton.setOnClickListener {
            findNavController().navigate(com.example.mandatorycatapp.R.id.action_FirstFragment_to_fragment_login)
        }
         */

        //sort button and filter
        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> catsViewModel.sortByName()
                1 -> catsViewModel.sortByNameDesending()
                2 -> catsViewModel.sortByDes()
            }
        }

        binding.buttonFilter.setOnClickListener {
            val name = binding.edittextFilterName.text.toString().trim()
            if (name.isBlank()) {
                binding.edittextFilterName.error = "No name"
                return@setOnClickListener
            }
            catsViewModel.filterName(name)
        }

        // when create cat button is clicked, move to create cat fragment
        binding.fabAddCat.setOnClickListener {
            val action =
                FirstFragmentDirections.actionFirstFragmentToCreateCatFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}