/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.pcdongthap.tracnghiemspc;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/** Application class that initializes, loads and show ads when activities change states. */
public class MyApplication extends Application {

    public static final String CHANEL_ID = "pust_notification_id";
    //    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
//        this.registerActivityLifecycleCallbacks(this);

        createChannelNotification();
//
//
//        // Log the Mobile Ads SDK version.
//        Log.d(TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion());
////ad
//        MobileAds.initialize(
//                this,
//                new OnInitializationCompleteListener() {
//                    @Override
//                    public void onInitializationComplete(
//                            @NonNull InitializationStatus initializationStatus) {}
//                });
//
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//        appOpenAdManager = new AppOpenAdManager();
////ad

    }

    //pust notification
    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, "PustNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    //pust notification

    //ad

//    /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
//    @OnLifecycleEvent(Event.ON_START)
//    protected void onMoveToForeground() {
//
//        // Show the ad (if available) when the app moves to foreground.
//        appOpenAdManager.showAdIfAvailable(currentActivity);
//    }

//    /** ActivityLifecycleCallback methods. */
//    @Override
//    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}
//
//    @Override
//    public void onActivityStarted(@NonNull Activity activity) {
//        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
//        // SDK or another activity class implemented by a third party mediation partner. Updating the
//        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
//        // one that shows the ad.
//        if (!appOpenAdManager.isShowingAd) {
//            currentActivity = activity;
//        }
//    }

//    @Override
//    public void onActivityResumed(@NonNull Activity activity) {}
//
//    @Override
//    public void onActivityPaused(@NonNull Activity activity) {}
//
//    @Override
//    public void onActivityStopped(@NonNull Activity activity) {}
//
//    @Override
//    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}
//
//    @Override
//    public void onActivityDestroyed(@NonNull Activity activity) {}
//
//    /**
//     * Shows an app open ad.
//     *
//     * @param activity the activity that shows the app open ad
//     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
//     */
//    public void showAdIfAvailable(
//            @NonNull Activity activity,
//            @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
//        // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
//        // class.
//        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
//    }
//
//    /**
//     * Interface definition for a callback to be invoked when an app open ad is complete
//     * (i.e. dismissed or fails to show).
//     */
//    public interface OnShowAdCompleteListener {
//        void onShowAdComplete();
//    }
//
//
//
//    /** Inner class that loads and shows app open ads. */
//    private class AppOpenAdManager {
//
//        private static final String LOG_TAG = "AppOpenAdManager";
////        ca-app-pub-9808019056055820/4788228294    /6499/example/app-open
//        private static final String AD_UNIT_ID = "ca-app-pub-9808019056055820/4788228294";
//        //idqc
//
//        private AppOpenAd appOpenAd = null;
//        private boolean isLoadingAd = false;
//        private boolean isShowingAd = false;
//
//        /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
//        private long loadTime = 0;
//
//        /** Constructor. */
//        public AppOpenAdManager() {}
//
//        /**
//         * Load an ad.
//         *
//         * @param context the context of the activity that loads the ad
//         */
//        private void loadAd(Context context) {
//            // Do not load ad if there is an unused ad or one is already loading.
//            if (isLoadingAd || isAdAvailable()) {
//                return;
//            }
//
//            isLoadingAd = true;
//            AdRequest request = new AdRequest.Builder().build();
//            AppOpenAd.load(
//                    context,
//                    AD_UNIT_ID,
//                    request,
//                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
//                    new AppOpenAdLoadCallback() {
//                        /**
//                         * Called when an app open ad has loaded.
//                         *
//                         * @param ad the loaded app open ad.
//                         */
//                        @Override
//                        public void onAdLoaded(AppOpenAd ad) {
//                            appOpenAd = ad;
//                            isLoadingAd = false;
//                            loadTime = (new Date()).getTime();
//
////                            Log.d(LOG_TAG, "onAdLoaded.");
////                            Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show();
//                        }
//
//                        /**
//                         * Called when an app open ad has failed to load.
//                         *
//                         * @param loadAdError the error.
//                         */
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError loadAdError) {
//                            isLoadingAd = false;
////                            Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
////                            Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//
//        /** Check if ad was loaded more than n hours ago. */
//        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
//            long dateDifference = (new Date()).getTime() - loadTime;
//            long numMilliSecondsPerHour = 3600000;
//            return (dateDifference < (numMilliSecondsPerHour * numHours));
//        }
//
//        /** Check if ad exists and can be shown. */
//        private boolean isAdAvailable() {
//            // Ad references in the app open beta will time out after four hours, but this time limit
//            // may change in future beta versions. For details, see:
//            // https://support.google.com/admob/answer/9341964?hl=en
//            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
//        }
//
//        /**
//         * Show the ad if one isn't already showing.
//         *
//         * @param activity the activity that shows the app open ad
//         */
//        private void showAdIfAvailable(@NonNull final Activity activity) {
//            showAdIfAvailable(
//                    activity,
//                    new OnShowAdCompleteListener() {
//                        @Override
//                        public void onShowAdComplete() {
//                            // Empty because the user will go back to the activity that shows the ad.
//                        }
//                    });
//        }
//
//        /**
//         * Show the ad if one isn't already showing.
//         *
//         * @param activity the activity that shows the app open ad
//         * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
//         */
//        private void showAdIfAvailable(
//                @NonNull final Activity activity,
//                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
//            // If the app open ad is already showing, do not show the ad again.
//            if (isShowingAd) {
//                return;
//            }
//
//            // If the app open ad is not available yet, invoke the callback then load the ad.
//            if (!isAdAvailable()) {
//                onShowAdCompleteListener.onShowAdComplete();
//                loadAd(activity);
//                return;
//            }
//
//
//            appOpenAd.setFullScreenContentCallback(
//                    new FullScreenContentCallback() {
//                        /** Called when full screen content is dismissed. */
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            // Set the reference to null so isAdAvailable() returns false.
//                            appOpenAd = null;
//                            isShowingAd = false;
//
//
//                            onShowAdCompleteListener.onShowAdComplete();
//                            loadAd(activity);
//                        }
//
//                        /** Called when fullscreen content failed to show. */
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(AdError adError) {
//                            appOpenAd = null;
//                            isShowingAd = false;
//
//                            onShowAdCompleteListener.onShowAdComplete();
//                            loadAd(activity);
//                        }
//
//                        /** Called when fullscreen content is shown. */
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//                        }
//                    });
//
//            isShowingAd = true;
//            appOpenAd.show(activity);
//        }
//    }
    //ad

}