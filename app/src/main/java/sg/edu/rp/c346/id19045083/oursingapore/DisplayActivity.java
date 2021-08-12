package sg.edu.rp.c346.id19045083.oursingapore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    Button btnShow5Stars;
    ListView lv;
    ArrayList<Food> foodsArrayList;
    final boolean[] checker = {true};
    Spinner spn;
    ArrayList<String> stars;
    ArrayAdapter<String> SPNaa;
    CustomAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnShow5Stars = findViewById(R.id.buttonShow5Stars);
        spn = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(DisplayActivity.this);
        foodsArrayList = new ArrayList<Food>();
        foodsArrayList.addAll(dbh.getAllFoods());
        foodAdapter = new CustomAdapter(DisplayActivity.this, R.layout.row, foodsArrayList);
        lv.setAdapter(foodAdapter);

        stars = new ArrayList<String>();
        stars.add("All Records");
        for (int i=0; i < foodsArrayList.size(); i++) {
            String star = String.valueOf(foodsArrayList.get(i).getStars()) + " Star(s)";
            if (!stars.contains(star)){
                stars.add(star);
            }
        }
        SPNaa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stars);
        SPNaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(SPNaa);

        btnShow5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn.setSelection(0);
                foodsArrayList.clear();
                if (checker[0] == true) {   // Show all
                    foodsArrayList.addAll(dbh.getAllFoods());
                    foodAdapter.notifyDataSetChanged();
                    checker[0] = false;
                }
                else if (checker[0] == false) { // Show all with 5 stars only
                    foodsArrayList.addAll(dbh.getAllFoodsWithStars(5));
                    foodAdapter.notifyDataSetChanged();
                    checker[0] = true;
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food data = foodsArrayList.get(position);
                Intent i = new Intent(DisplayActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = stars.get(position);
                select = select.split(" ")[0];
                Log.d("Spinner", "Position: " + select);
                if (position!=0) {
                    int star = Integer.parseInt(select);
                    foodsArrayList.clear();
                    foodsArrayList.addAll(dbh.getAllFoodsWithStars(star));
                }
                else {
                    foodsArrayList.clear();
                    foodsArrayList.addAll(dbh.getAllFoods());
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    } //onCreate Method

    @Override
    protected void onResume() {
        super.onResume();
        checker[0] = true;
        btnShow5Stars.performClick();

        stars.clear();
        stars.add("All Records");
        for (int i=0; i < foodsArrayList.size(); i++) {
            String star = String.valueOf(foodsArrayList.get(i).getStars()) + " Star(s)";
            if (!stars.contains(star)){
                stars.add(star);
            }
        }
        SPNaa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stars);
        SPNaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(SPNaa);
    }

}
