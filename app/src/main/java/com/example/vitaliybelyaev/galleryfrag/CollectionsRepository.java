package com.example.vitaliybelyaev.galleryfrag;

import com.example.vitaliybelyaev.galleryfrag.domain.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsRepository {
    private static final CollectionsRepository INSTANCE = new CollectionsRepository();

    private Map<Integer, Collection> storage;

    private CollectionsRepository(){
        this.storage = new HashMap<>();
    }

    public static CollectionsRepository getInstance() {
        return INSTANCE;
    }

    public Collection getById(int id){
        return storage.get(id);
    }

    public void save(Collection collection){
        storage.put(collection.getId(),collection);
    }

    public List<Collection> getAllCollections(){
        return new ArrayList<>(storage.values());
    }
}
