package ca.cmpt213.asn5.demo.service;

import ca.cmpt213.asn5.demo.models.ResponseModel;
import ca.cmpt213.asn5.demo.models.Superhuman;

public interface SuperhumanService {
    ResponseModel addSuperhuman(Superhuman superhuman);

    ResponseModel fetchAllSuperhumans();

    ResponseModel getSuperhumanInfo(long sid);

    ResponseModel deleteSuperhero(long sid);  
}
