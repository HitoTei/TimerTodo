package com.example.timertodo

import android.app.Application
import androidx.room.Room
import com.example.timertodo.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomApplication : Application()