package com.example.kanji_prototype_1.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.ui.model.Kanji;
import java.util.List;

public class KanjiAdapter extends RecyclerView.Adapter<KanjiAdapter.KanjiViewHolder> {

    private List<Kanji> kanjiList;

    public KanjiAdapter(List<Kanji> kanjiList) {
        this.kanjiList = kanjiList;
    }

    @NonNull
    @Override
    public KanjiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kanji_item, parent, false);
        return new KanjiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KanjiViewHolder holder, int position) {
        Kanji kanji = kanjiList.get(position);
        holder.kanjiText.setText(kanji.getCharacter());  // Assuming getCharacter() returns the Kanji character
        holder.heisigText.setText("ID: " + kanji.getId() + " - " + kanji.getHeisig());
        holder.meaningText.setText(kanji.getMeaning());
    }

    @Override
    public int getItemCount() {
        return kanjiList.size();
    }

    // Method to update the data in the adapter
    public void updateData(List<Kanji> newKanjiList) {
        kanjiList = newKanjiList;
        notifyDataSetChanged();
    }

    static class KanjiViewHolder extends RecyclerView.ViewHolder {
        TextView kanjiText, heisigText, meaningText;

        KanjiViewHolder(View itemView) {
            super(itemView);
            kanjiText = itemView.findViewById(R.id.kanji_text);
            heisigText = itemView.findViewById(R.id.heisig_text);
            meaningText = itemView.findViewById(R.id.meaning_text);
        }
    }
}
