package com.aa.thrivetrack.helpers;

import android.content.Context;
import android.view.View;

import com.aa.thrivetrack.dialogs.PatchDialog;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.SessionStorage;

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
