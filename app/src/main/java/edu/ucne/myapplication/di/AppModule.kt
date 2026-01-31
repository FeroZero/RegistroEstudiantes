package edu.ucne.myapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.myapplication.data.estudiantes.local.database.EstudianteDb
import edu.ucne.myapplication.data.estudiantes.local.repositoryimpl.AsignaturaRepositoryImpl
import edu.ucne.myapplication.data.estudiantes.local.repositoryimpl.EstudianteRepositoryImpl
import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideEstudianteDb(@ApplicationContext appContext: Context) =
            Room.databaseBuilder(
                appContext,
                klass = EstudianteDb::class.java,
                name = "Estudiante.db"
            ).fallbackToDestructiveMigration()
                .build()

        @Provides
        fun provideEstudianteDao(db: EstudianteDb) = db.estudianteDao()
        @Provides
        fun provideAsignaturaDao(db: EstudianteDb) = db.asignaturaDao()
    }
    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        impl: EstudianteRepositoryImpl
    ): EstudianteRepository

    @Binds
    @Singleton
    abstract fun bindAsignaturaRepository(
        impl: AsignaturaRepositoryImpl
    ): AsignaturaRepository
}




