package com.example.pythoncalculation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.pythoncalculation.databinding.ActivityMainBinding;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Python py;
    private PyObject inputReaderModule;
    private PyObject mondrianModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializePython();
    }

    private void initializePython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        py = Python.getInstance();
        inputReaderModule = py.getModule("algorithm.input_reader");
        mondrianModule = py.getModule("algorithm.mondrian");
    }

    public void readButtonPythonRun(View view) {
        new ReadCsvTask(this).execute();
    }

    public void onAnonymizeButtonClick(View view, int kValue) {
        new AnonymizeTask(this, kValue).execute();
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
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private static class ReadCsvTask extends AsyncTask<Void, Void, String> {
        private WeakReference<MainActivity> activityReference;

        ReadCsvTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... voids) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;

            try (PyObject pyObjectResult = activity.inputReaderModule.callAttr("get_csvfile", "dataset.csv")) {
                return pyObjectResult.toString();
            } catch (Exception e) {
                Log.e(TAG, "Error reading CSV file", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (result != null) {
                activity.binding.textViewOutput.setText(result);
            } else {
                activity.binding.textViewOutput.setText(activity.getString(R.string.error_message, "Failed to read CSV"));
            }
        }
    }

    private static class AnonymizeTask extends AsyncTask<Void, Void, String> {
        private WeakReference<MainActivity> activityReference;
        private int kValue;

        AnonymizeTask(MainActivity context, int kValue) {
            activityReference = new WeakReference<>(context);
            this.kValue = kValue;
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            int durationEstimate = 70 / kValue;
            StringBuilder message = new StringBuilder()
                    .append("Anonymization button (K=").append(kValue).append(") clicked!\n")
                    .append("Processing in the backend. Please wait for a short moment\n")
                    .append("It might take ").append(durationEstimate).append("-").append(durationEstimate + 10).append(" seconds\n")
                    .append("Once the anonymization is completed, the result will be shown above");
            activity.showToast(message.toString());
        }

        @Override
        protected String doInBackground(Void... voids) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;

            try (PyObject pyObjectAnonymizedDataResult = activity.mondrianModule.callAttr("anonymize_execute", kValue)) {
                return pyObjectAnonymizedDataResult.toString();
            } catch (Exception e) {
                Log.e(TAG, "Error during anonymization", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (result != null) {
                Log.d(TAG, "MainActivity: Anonymization successfully completed");
                activity.showToast("Anonymization is completed!");
                activity.binding.textViewOutput.setText(result);
            } else {
                activity.binding.textViewOutput.setText(activity.getString(R.string.error_message, "Anonymization failed"));
                activity.showToast("Anonymization failed");
            }
        }
    }
}











//package com.example.pythoncalculation;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.chaquo.python.android.AndroidPlatform;
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    private TextView textViewOutput;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize UI components
//        textViewOutput = findViewById(R.id.textViewOutput);
//
//        // Initialize Python environment if not started
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//    }
//    public void readButtonPythonRun(View view) {
//        try (PyObject pyObjectResult = Python.getInstance()
//                .getModule("algorithm.input_reader")
//                .callAttr("get_csvfile", "dataset.csv")) {
//
//            textViewOutput.setText(pyObjectResult.toString());
//        } catch (Exception e) {
//            Log.e(TAG, "Error reading CSV file", e);
//            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
//        }
//    }
//    public void onAnonymizeButtonClick(View view, int kValue) {
//        try {
//            int durationEstimate = 70 / kValue;
//
//            showToast("Anonymization button (K=" + kValue + ") clicked!");
//            showToast("Processing in the backend. Please wait for a short moment");
//            showToast("It might take " + durationEstimate + "-" + (durationEstimate + 10) + " seconds");
//            showToast("Once the anonymization is completed, the result will be shown above");
//
//            try (PyObject pyObjectAnonymizedDataResult = Python.getInstance()
//                    .getModule("algorithm.mondrian")
//                    .callAttr("anonymize_execute", kValue)) {
//
//                Log.d(TAG, "MainActivity: Anonymization successfully completed");
//                showToast("Anonymization is completed!");
//                textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "Error during anonymization", e);
//            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
//            showToast("Anonymization failed");
//        }
//    }
//    public void onAnonymizeButtonClick_k2(View view) {
//        onAnonymizeButtonClick(view, 2);
//    }
//    public void onAnonymizeButtonClick_k5(View view) {
//        onAnonymizeButtonClick(view, 5);
//    }
//    public void onAnonymizeButtonClick_k10(View view) {
//        onAnonymizeButtonClick(view, 10);
//    }
//    private void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//    }
//}