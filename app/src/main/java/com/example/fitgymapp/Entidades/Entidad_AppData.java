package com.example.fitgymapp.Entidades;

import android.app.Application;


import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class Entidad_AppData extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "Ad7pJsB_173mUVqGevpwvRlt-gDwxunCsnJq5LCn90_tD_JJmSFf-cdgZDkntftswcTRLQ0_oQNl-AQx",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "com.example.fitgymapp://paypalpay"
        ));
    }
}
