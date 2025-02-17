package com.example.pythoncalculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
    public void readButtonPythonRun(View view) {
        try (PyObject pyObjectResult = Python.getInstance()
                .getModule("algorithm.input_reader")
                .callAttr("get_csvfile", "dataset.csv")) {

            textViewOutput.setText(pyObjectResult.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error reading CSV file", e);
            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
        }
    }
    public void onAnonymizeButtonClick(View view, int kValue) {
        try {
            int durationEstimate = 70 / kValue;
            
            showToast("Anonymization button (K=" + kValue + ") clicked!");
            showToast("Processing in the backend. Please wait for a short moment");
            showToast("It might take " + durationEstimate + "-" + (durationEstimate + 10) + " seconds");
            showToast("Once the anonymization is completed, the result will be shown above");

            try (PyObject pyObjectAnonymizedDataResult = Python.getInstance()
                    .getModule("algorithm.mondrian")
                    .callAttr("anonymize_execute", kValue)) {

                Log.d(TAG, "MainActivity: Anonymization successfully completed");
                showToast("Anonymization is completed!");
                textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
            showToast("Anonymization failed");
        }
    }
    public void onAnonymizeButtonClick_k2(View view) {
        onAnonymizeButtonClick(view, 2);
    }
    public void onAnonymizeButtonClick_k5(View view) {
        onAnonymizeButtonClick(view, 5);
    }
    public void onAnonymizeButtonClick_k10(View view) {
        onAnonymizeButtonClick(view, 10);
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}