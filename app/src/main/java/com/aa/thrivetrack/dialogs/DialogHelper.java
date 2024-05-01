package com.aa.thrivetrack.dialogs;

import android.content.Context;
import android.view.View;

public class DialogHelper {
    public static View.OnClickListener openPatchDialog(Context context, String mode){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchDialog pd = new PatchDialog(context, mode);
                pd.show();
            }
        };
    }
}
