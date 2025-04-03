package cslori.runique.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import cslori.runique.MainViewModel
import cslori.runique.RuniqueApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.io.File
import javax.crypto.AEADBadTagException

val appModule = module {
    single<SharedPreferences> {
        try {
            EncryptedSharedPreferences(
                androidApplication(),
                "auth_pref",
                MasterKey(androidApplication()),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: AEADBadTagException) {
            val sharedPrefsFile =
                File(androidApplication().filesDir.parent, "shared_prefs/auth_pref.xml")
            if (sharedPrefsFile.exists()) {
                sharedPrefsFile.delete()
            }
            EncryptedSharedPreferences(
                androidApplication(),
                "auth_pref",
                MasterKey(androidApplication()),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    single<CoroutineScope> {
        (androidApplication() as RuniqueApp).applicationScope
    }
    viewModelOf(::MainViewModel)
}