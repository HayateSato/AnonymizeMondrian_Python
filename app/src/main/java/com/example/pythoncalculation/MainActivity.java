package com.example.pythoncalculation;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        // Initialize TextView
        textViewOutput = findViewById(R.id.textViewOutput);

        // Initialize Python environment if not started
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }


    public void readButtonPythonRun(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to read the CSV file
            PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
                    .callAttr("get_csvfile", "dataset.csv"); // function name and CSV file path

            // Set the result to the TextView
//            textViewOutput.setText(pyObjectResult.toString());
//            ((ViewGroup) findViewById(R.id.tableContainer)).addView(new TableUIBuilder(this, pyObjectResult).build());
        } catch (Exception e) {
            // Handle any exceptions
            textViewOutput.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public void readButtonPythonRun(View view) {
//        String CsvFilePath = "/data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv"
//        DataFrame studentRecords = CSVReader.readCSV(CsvFilePath);
//    }


    public void onAnonymizeButtonClick(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to read the CSV file
            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
                    .callAttr("anonymize_execute"); // function name and CSV file path

            Log.d(TAG, "MainActivity: anonymization is scuccesufully completed");
            textViewOutput.setText(pyObjectResult.toString());



            // Set the result to the TextView
//            textViewOutput.setText(pyObjectResult.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
            // Handle any exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
        }
    }
}

//    public void onAnonymizeButtonClick(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
//                    .callAttr(
//                            "run_anonymize",
//                            new String[]{"sex", "age", "race", "marital-status", "education", "native-country", "workclass", "occupation"}, // qi_list
//                            new String[]{"salary-class"}, // sensitive_attributes
//                            new String[]{"ID", "soc_sec_id", "given_name", "surname"}, // identifier
//                            String dataFilePath = "/data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv" // Adjust the path to your dataset
//                            String hierarchyDir =  "/data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/hierarchy"// Adjust the path to hierarchy files
//                    );
//
//            // Set the result to the TextView
//            textViewOutput.setText(pyObjectResult.toString());
//        } catch (Exception e) {
//            // Handle any exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }





//    public void onAnonymizeButtonClick(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Define the parameters required by the Python function
//            String[] qi_list = {"sex", "age", "race", "marital-status", "education", "native-country", "workclass", "occupation"};
//            String data_file = "/data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv"; // Adjust the path to your dataset
//            String hierarchy_file_dir = "/data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/hierarchy"; // Adjust the path to hierarchy files
//            int kValue = 5;
//
//            // Pass arguments to the Python function
//            PyObject pyObjectResult = python.getModule("mondrian") // Replace with the Python file name (without .py)
//                    .callAttr(
//                            "run_anonymize","qi_list","data_file","hierarchy_file_dir","kValue"
////                            new String[]{"sex", "age", "race", "marital-status", "education", "native-country", "workclass", "occupation"}, // qi_list
////                            new String[]{"salary-class"}, // sensitive_attributes
////                            new String[]{"ID", "soc_sec_id", "given_name", "surname"}, // identifier
////                            dataFilePath,
////                            hierarchyDir,
////                            kValue
//                    );
//
//            // Display success message or anonymized data path
//            textViewOutput.setText("Anonymization completed. Anonymized file saved at:\n" + pyObjectResult.toString());
//        } catch (Exception e) {
//            // Handle exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }


