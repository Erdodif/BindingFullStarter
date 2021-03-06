package phil.petrik.bindingfull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;

import phil.petrik.bindingfull.data.Film;
import phil.petrik.bindingfull.data.RequestTask;

public class MainActivity extends AppCompatActivity {
    MaterialButton buttonNew;
    MaterialButton buttonSync;
    MaterialButton buttonClose;
    MaterialButton buttonCloseEditor;
    MaterialButton buttonAlter;
    MaterialButton buttonSend;
    ConstraintLayout layoutFilmEditor;
    ConstraintLayout layoutFilmInspector;
    TextView textViewFilmCim;
    TextView textViewFilmKategoria;
    TextView textViewFilmHossz;
    TextView textViewFilmErtekeles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleListeners();
        buttonSync.callOnClick();
    }

    private void handleListeners() {
        buttonNew.setOnClickListener(($)->{
            layoutFilmEditor.setVisibility(View.VISIBLE);
            layoutFilmInspector.setVisibility(View.GONE);
        });
        buttonSync.setOnClickListener(($) -> {
            setFilms();
        });
        buttonClose.setOnClickListener(($) -> {
            layoutFilmInspector.setVisibility(View.GONE);
        });
        buttonCloseEditor.setOnClickListener(($) -> {
            layoutFilmEditor.setVisibility(View.GONE);
        });
        buttonAlter.setOnClickListener(($) -> {
            layoutFilmEditor.setVisibility(View.VISIBLE);
            layoutFilmInspector.setVisibility(View.GONE);
        });
        buttonSend.setOnClickListener(($) -> {
            sendFilm(Film.emptyFilm());
        });
    }

    private void init(){
        buttonNew = findViewById(R.id.button_New);
        buttonSync = findViewById(R.id.button_Sync);
        buttonClose = findViewById(R.id.buttonClose);
        buttonCloseEditor = findViewById(R.id.buttonCloseEditor);
        buttonAlter = findViewById(R.id.buttonAlter);
        buttonSend = findViewById(R.id.buttonSend);
        layoutFilmEditor = findViewById(R.id.layout_Film_Editor);
        layoutFilmInspector = findViewById(R.id.layout_Film_Inspector);
        textViewFilmCim = findViewById(R.id.filmCim);
        textViewFilmKategoria = findViewById(R.id.filmKategoria);
        textViewFilmHossz = findViewById(R.id.filmHossz);
        textViewFilmErtekeles = findViewById(R.id.filmErtekeles);
    }

    private void setFilm(int id) {
        try {
            //TODO
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setFilms() {
        try {
            //TODO
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFilm(Film film) {
        if (film.getId() != null) {
            sendFilm(film, "PATCH");
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("M??dos??t??s");
        alertDialog.setMessage("Elv??gzi a m??dos??t??sokat?");
        alertDialog.setPositiveButton("Igen", (dialogInterface, i) -> {
            Toast.makeText(MainActivity.this, "Film: "+film.toString(), Toast.LENGTH_SHORT).show();
            sendFilm(film, "POST");
        });
        alertDialog.setNegativeButton("Nem", (dialogInterface, i) -> {
            layoutFilmEditor.setVisibility(View.GONE);
        });
        alertDialog.show();
    }
    private void sendFilm(Film film, String method) {
        Log.d("FilmJSON", film.toJson());
        try {
            RequestTask requestTask = new RequestTask("/film" + (film.getId() == null ? "" : "/"+film.getId().toString()), method, film.toJson());
            requestTask.setLastTask(() -> {
                String toastText = "m??dos??t??s";
                if (method.equals("POST")) {
                    toastText = "felv??tel";
                }
                if (requestTask.getResponse().getCode() < 300) {
                    Toast.makeText(MainActivity.this, "Sikeres " + toastText, Toast.LENGTH_SHORT).show();
                    layoutFilmEditor.setVisibility(View.GONE);
                    return;
                }
                Log.d("H??v??s / " + requestTask.getResponse().getCode(), requestTask.getResponse().getContent());
                Toast.makeText(MainActivity.this, "Sikertelen " + toastText, Toast.LENGTH_SHORT).show();
            });
            requestTask.execute();
            setFilms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteFilm(int id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("T??rl??s");
        alertDialog.setMessage("Biztos t??r??lni szeretn???");
        alertDialog.setPositiveButton("Igen", (dialogInterface, i) -> {
            try {
                RequestTask requestTask = new RequestTask("/film/" + id, "DELETE");
                requestTask.setLastTask(() -> {
                    if (requestTask.getResponse().getCode() < 300) {
                        Toast.makeText(MainActivity.this, "Sikeresen t??r??lve!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d("H??v??s / " + requestTask.getResponse().getCode(), requestTask.getResponse().getContent());
                    Toast.makeText(MainActivity.this, "Sikertelen t??rl??s!", Toast.LENGTH_SHORT).show();
                });
                requestTask.execute();
                setFilms();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        alertDialog.setNegativeButton("Nem", null);
        alertDialog.show();
    }

    private MaterialButton createFilmButton(Film film) {
        MaterialButton buttonFilm = new MaterialButton(MainActivity.this);
        buttonFilm.setText(film.getCim());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonFilm.setLayoutParams(lp);
        buttonFilm.setOnClickListener(($) -> {
            setFilm(film.getId());
        });
        buttonFilm.setOnLongClickListener(($) -> {
            deleteFilm(film.getId());
            return true;
        });
        return buttonFilm;
    }

}