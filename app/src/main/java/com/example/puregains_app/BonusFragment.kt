package com.example.puregains_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.math.E

class RewardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_bonus, container, false)

        val reward_text = view.findViewById<TextView>(R.id.account_reward_text)
        val credit_text = view.findViewById<TextView>(R.id.account_credit_text)
        val user_type = view.findViewById<Button>(R.id.account_type_button)

        var reward_points : Int = -1
        var credit : Int = -1
        var premium : Boolean = false
        runBlocking(Dispatchers.IO) {
            reward_points = Connect.getReward(
                Auth.getUsername(requireActivity()),
                Auth.getToken(requireActivity())
            )

            credit = Connect.getCredit(
                Auth.getUsername(requireActivity()),
                Auth.getToken(requireActivity())
            )
            premium = Auth.isPremium(requireActivity())
            Log.i("USERNAME",premium.toString())
        }

        reward_text.text = reward_points.toString()
        credit_text.text = credit.toString()

        if (premium) {
            user_type.text = "Premium"
        } else {
            user_type.text = "Free"
        }

        // set button listeners
        val redeem_credit_button = view.findViewById<Button>(R.id.account_redeem_two_text)
        val redeem_premium_button = view.findViewById<Button>(R.id.account_redeem_four_text)
        redeem_credit_button.setOnClickListener {
            val updated_credit_reward = redeemReward(
                    200,
                    10
            )

            reward_text.text = updated_credit_reward.first.toString()
            credit_text.text = updated_credit_reward.second.toString()
        }

        redeem_premium_button.setOnClickListener {
            val updated_credit_reward = redeemReward(
                    600,
                    0
            )

            runBlocking(Dispatchers.IO) {
                Connect.updatePremium(
                        Auth.getUsername(requireActivity()),
                        true,
                        Auth.getToken(requireActivity())
                )
            }

            reward_text.text = updated_credit_reward.first.toString()
            user_type.text = "Premium"
        }

        return view
    }

    fun redeemReward(
            reward_loss : Int,
            credit_gain : Int,
    ) : Pair<Int,Int>{
        var credit : Int = 0
        var reward : Int = 0
        var response : Pair<Int,Int> = Pair(0,0)

        try  {
            runBlocking(Dispatchers.IO) {
                credit = Connect.getCredit(
                        Auth.getUsername(requireActivity()),
                        Auth.getToken(requireActivity())
                )

                reward = Connect.getReward(
                        Auth.getUsername(requireActivity()),
                        Auth.getToken(requireActivity())
                )

                val new_credit = credit + credit_gain
                val new_reward = reward - reward_loss

                if (new_reward < 0) {
                    throw Exception("Redeem points would be negative")
                }

                Connect.updateReward(
                        Auth.getUsername(requireActivity()),
                        new_reward,
                        Auth.getToken(requireActivity())
                )

                Connect.updateCredit(
                        Auth.getUsername(requireActivity()),
                        new_credit,
                        Auth.getToken(requireActivity())
                )
                response = Pair(new_reward, new_credit)
            }
        } catch (e : Exception) {
            errorMessage("Unable to redeem")
        }
        return response
    }

    fun errorMessage(message : String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}