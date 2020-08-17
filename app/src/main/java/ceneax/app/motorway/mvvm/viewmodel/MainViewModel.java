package ceneax.app.motorway.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ceneax.app.motorway.bean.GLVideoTree;
import ceneax.app.motorway.bean.GSVideoTree;
import ceneax.app.motorway.mvvm.repository.MainRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<GSVideoTree>> gsVideoTrees;
    private MutableLiveData<List<GLVideoTree>> glVideoTrees;

    public MutableLiveData<List<GSVideoTree>> getGsVideoTrees() {
        if (gsVideoTrees == null) {
            gsVideoTrees = new MutableLiveData<>();
            gsVideoTrees.setValue(new ArrayList<>());
        }
        return gsVideoTrees;
    }

    public void loadGSVideoTrees(String name, int code) {
        Call<List<GSVideoTree>> call = MainRepository.getInstance().request.getGSVideoTree(name, code);
        call.enqueue(new Callback<List<GSVideoTree>>() {
            @Override
            public void onResponse(Call<List<GSVideoTree>> call, Response<List<GSVideoTree>> response) {
                gsVideoTrees.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GSVideoTree>> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<List<GLVideoTree>> getGlVideoTrees() {
        if (glVideoTrees == null) {
            glVideoTrees = new MutableLiveData<>();
            glVideoTrees.setValue(new ArrayList<>());
        }
        return glVideoTrees;
    }

    public void loadGLVideoTrees(String name, int code) {
        Call<List<GLVideoTree>> call = MainRepository.getInstance().request.getGLVideoTree(name, code);
        call.enqueue(new Callback<List<GLVideoTree>>() {
            @Override
            public void onResponse(Call<List<GLVideoTree>> call, Response<List<GLVideoTree>> response) {
                glVideoTrees.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GLVideoTree>> call, Throwable t) {
            }
        });
    }

}
