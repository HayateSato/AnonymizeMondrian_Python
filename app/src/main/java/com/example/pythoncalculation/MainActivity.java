package com.example.pythoncalculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        textViewOutput = findViewById(R.id.textViewOutput);

        // Initialize Python environment if not started
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    // Read button: function in input_reader.py is called here
    public void readButtonPythonRun(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to read the CSV file
            PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
                    .callAttr("get_csvfile", "dataset.csv"); // function name and input (CSV file name)
            textViewOutput.setText(pyObjectResult.toString());

        } catch (Exception e) {
            // Handle any exceptions
            textViewOutput.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Anonymize button: triggers anonymizing the CSV file using mondrian.py
    public void onAnonymizeButtonClick(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to anonymize the CSV file
            PyObject pyObjectAnonymizedDataResult = python.getModule("mondrian") // Python file name (without .py)
                    .callAttr("anonymize_execute");  // function name in the python file

            Log.d(TAG, "MainActivity: Anonymization successfully completed");
            textViewOutput.setText(pyObjectAnonymizedDataResult.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
            textViewOutput.setText("Error: " + e.getMessage());
        }
    }
}
