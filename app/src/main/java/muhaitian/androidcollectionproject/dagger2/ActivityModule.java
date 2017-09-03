package muhaitian.androidcollectionproject.dagger2;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import muhaitian.androidcollectionproject.model.Student;

/**
 * Created by muhaitian on 2017/8/29.
 */

@Module
public class ActivityModule {
    @StudentForContext
    @Provides
    @Singleton
    Student providerStudent(Context context) {
        return new Student(context);
    }

    @StudentForName
    @Provides
    @Singleton
    Student providerStudentName(){
        Student student = new Student();
        student.setName("student name is muhaitian-----shadan");
        return student;
    }
}
