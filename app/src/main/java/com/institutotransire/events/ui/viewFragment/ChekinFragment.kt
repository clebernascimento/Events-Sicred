package com.institutotransire.events.ui.viewFragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.institutotransire.events.R
import com.institutotransire.events.databinding.FragmentChekinBinding
import com.institutotransire.events.ui.viewActivity.MainActivity

class ChekinFragment : Fragment(), View.OnClickListener {

    private lateinit var chekinBinding: FragmentChekinBinding
    private var main: MainActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        chekinBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chekin, container, false)

        components()

        return chekinBinding.getRoot()
    }

    /**
     * Metodo para inicializar os componentes
     */
    fun components() {
        main = activity as MainActivity?

        chekinBinding.btnCheckinSucess.setOnClickListener(this)

        onBackPressedFragment()
    }

    /**
     * Metodo para click
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCheckinSucess -> goToHome()
        }
    }

    /*Metodo para o backSpace do celular*/
    private fun onBackPressedFragment() {
        chekinBinding.root.isFocusableInTouchMode = true
        chekinBinding.root.requestFocus()
        chekinBinding.root.setOnKeyListener { view: View?, i: Int, keyEvent: KeyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                goToHome()
                return@setOnKeyListener true
            }
            false
        }
    }

    /*Metodo para voltar*/
    private fun goToHome() {
        val fragment: Fragment = MainFragment()
        main!!.openFragment(fragment)
    }

    companion object {
        /**
         * Metodo para instaciar o fragmento
         */
        @JvmStatic
        fun newInstance(): ChekinFragment {
            return ChekinFragment()
        }
    }
}