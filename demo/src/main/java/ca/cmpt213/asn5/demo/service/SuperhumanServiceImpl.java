package ca.cmpt213.asn5.demo.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.cmpt213.asn5.demo.models.ResponseModel;
import ca.cmpt213.asn5.demo.models.Superhuman;
import ca.cmpt213.asn5.demo.utils.Constants;

@Service
public class SuperhumanServiceImpl implements SuperhumanService {

    @Override
    public ResponseModel addSuperhuman(Superhuman superhuman) {
        ResponseModel responseModel = new ResponseModel();

        List<Superhuman> superhumanData = readJsonData();
        Superhuman superhumanPresent = null;

        // in case user wants to add superhero of same ID in database
        // If user cannot input ID as an option, then this error should never happen
        if (superhumanData != null) {
            superhumanPresent = superhumanData.parallelStream()
                    .filter(sup -> Long.valueOf(sup.getId()).equals(superhuman.getId()))
                    .findAny()
                    .orElse(null);
        } else {
            superhumanData = new ArrayList<>();
        }

        if (superhumanPresent == null) {
            superhumanData.add(superhuman);

            boolean status = writeJsonData(superhumanData);

            if (status) {
                responseModel.setStatus(Constants.SUCCESS);
                responseModel.setData(Constants.SUPERHUMAN_ADDED_SUCCESSFULLY);
            }
        } else {
             responseModel.setStatus(Constants.SUPERHUMAN_ALREADY_PRESENT);
        }
        return responseModel;
    }

    public List<Superhuman> readJsonData() {
        List<Superhuman> superhumans = new ArrayList<>();
        try {
            String json = Files.readString(Path.of("data/superhumans.json"));
            superhumans = new Gson().fromJson(json, new TypeToken<List<Superhuman>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return superhumans;
    }

    public boolean writeJsonData(List<Superhuman> superhumansData) {
        boolean status = false;

        try (FileWriter file = new FileWriter("data/superhumans.json")) {
            file.write(new Gson().toJson(superhumansData));
            file.flush();

            status = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public ResponseModel fetchAllSuperhumans() {
        ResponseModel responseModel = new ResponseModel();

        List<Superhuman> superhumans = readJsonData();

        responseModel.setStatus(Constants.SUCCESS);
        responseModel.setData(superhumans);

        return responseModel;
    }

    @Override
    public ResponseModel getSuperhumanInfo(long sid) {
        ResponseModel responseModel = new ResponseModel();

        List<Superhuman> superhumans = readJsonData();

        Superhuman sh = superhumans.parallelStream()
                    .filter(sup -> Long.valueOf(sup.getId()).equals(sid))
                    .findAny()
                    .orElse(null);

        if (sh != null) {
            responseModel.setStatus(Constants.SUCCESS);
            responseModel.setData(sh);
        } else {
            responseModel.setStatus(Constants.FAIL);
            responseModel.setData(Constants.SUPERHUMAN_NOT_FOUND);
        }

        return responseModel;
    }

    @Override
    public ResponseModel deleteSuperhero(long sid) {
       ResponseModel responseModel = new ResponseModel();

       List<Superhuman> superhumans = readJsonData();

       boolean deleted = false;

       for (Superhuman sup : superhumans) {
        Long superhumanId = Long.valueOf(sup.getId());
        
        if (superhumanId.equals(sid)) {
            superhumans.remove(sup);
            deleted = true;
            break;  
        }
    }

       writeJsonData(superhumans);

       if (deleted) {
        responseModel.setStatus(Constants.SUCCESS);
        responseModel.setData(Constants.SUPERHUMAN_DELETED_SUCCESSFULLY);
       } else {
        responseModel.setStatus(Constants.FAIL);
        responseModel.setData(Constants.SUPERHUMAN_NOT_FOUND);
       }

       return responseModel;
    }
}
