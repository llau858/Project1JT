package tempconverter.tummala.com.tempconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.text.NumberFormat;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;

public class TempConverterActivity extends Activity implements OnEditorActionListener
{
    //define private instance variables
    private TextView celsiusDegreesLabel;
    private EditText fahrenheitDegreesEditable;
    private SharedPreferences savedValues;
    private String fahrenheitDegreesString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get references to the widgets
        celsiusDegreesLabel = (TextView) findViewById(R.id.celsiusDegreesLabel);
        fahrenheitDegreesEditable = (EditText) findViewById(R.id.fahrenheitDegreesEditable);

        // set up listeners
        fahrenheitDegreesEditable.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues",MODE_PRIVATE);
    }

    public void convertFahrenheitToCelsius()
    {
        // get the fahrenheit temp
        fahrenheitDegreesString = fahrenheitDegreesEditable.getText().toString();
        float fahrenheitDegrees;
        if(fahrenheitDegreesString.equals(""))
        {
            fahrenheitDegrees = 0;
        }
        else
        {
            fahrenheitDegrees  = Float.parseFloat(fahrenheitDegreesString);
        }

        //convert the fahrenheit temp to celsius
        float celsiusDegrees = ((fahrenheitDegrees - 32)*5/9);
        NumberFormat celciusF = NumberFormat.getNumberInstance();
        celsiusDegreesLabel.setText(celciusF.format(celsiusDegrees));

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            convertFahrenheitToCelsius();
        }

        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //get the instance variables
        fahrenheitDegreesString = savedValues.getString("fahrenheitDegreesString","");
        fahrenheitDegreesEditable.setText(fahrenheitDegreesString);

        convertFahrenheitToCelsius();

    }

    @Override
    public void onPause()
    {
        //save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitDegreesString",fahrenheitDegreesString);
        editor.commit();
        super.onPause();
    }


}
