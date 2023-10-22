package com.example.semenov_lab_1.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.semenov_lab_1.R
import com.example.semenov_lab_1.databinding.RecipeBinding

class RcAdapter(val listener: Listener): RecyclerView.Adapter<RcAdapter.BlockHolder>() {

    val RecipeList=ArrayList<Recipe>()

    class BlockHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = RecipeBinding.bind(item)
        fun bind(recipe: Recipe, listener: Listener) = with(binding){
            textName.text=recipe.Name
            carder.setOnClickListener{
                listener.onClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recipe,parent,false)
        return  BlockHolder(view)
    }

    override fun onBindViewHolder(holder: BlockHolder, position: Int) {
        holder.bind(RecipeList[position], listener )
    }

    override fun getItemCount(): Int {
        return RecipeList.size
    }

    fun RecipeCreate(recipe: Recipe){
        var marker=true
        var l=0
        while (l<RecipeList.size) {
            if (recipe.Name == RecipeList[l].Name) {
                marker = false
                break
            }
            l += 1
        }
        if(marker)
            RecipeList.add(Recipe(recipe.Calorie, recipe.Time, recipe.Name, recipe.Ingredients, recipe.Difficulty))
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(recipe: Recipe)
    }
}