package ru.and.restapp.Cache;

import org.springframework.stereotype.Component;
import ru.and.restapp.DTO.GroupDTO;
import ru.and.restapp.DTO.StudentDTO;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



@Component
public class CacheManager {
    private final int maxSize = 3;
    private int currentSize = 0;
    private final ConcurrentHashMap<String, CacheEntity<StudentDTO>> studentDTOcache =  new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheEntity<GroupDTO>> groupDTOcache =  new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    public CacheManager() {
        executorService.scheduleAtFixedRate(this::clearExpiredCache, 0, 10, TimeUnit.SECONDS);
    }
    private void clearExpiredCache() {
        lock.writeLock().lock(); // Захватываем блокировку для записи
        try {
            long nowTimeInSeconds = Instant.now().getEpochSecond(); //в секундах
            Iterator<Map.Entry<String, CacheEntity<StudentDTO>>> iteratorStudent = studentDTOcache.entrySet().iterator();
            while(iteratorStudent.hasNext()){
                Map.Entry<String, CacheEntity<StudentDTO>> entryStudent = iteratorStudent.next();
                long entryTime = entryStudent.getValue().getTimeAddition();
                long timeDifference = nowTimeInSeconds - entryTime;
                if(timeDifference > 60){
                    iteratorStudent.remove();
                    currentSize--;
                }
            }
            nowTimeInSeconds = Instant.now().getEpochSecond();
            Iterator<Map.Entry<String, CacheEntity<GroupDTO>>> iteratorGroup = groupDTOcache.entrySet().iterator();
            while(iteratorGroup.hasNext()){
                Map.Entry<String, CacheEntity<GroupDTO>> entryGroup = iteratorGroup.next();
                long entryTime = entryGroup.getValue().getTimeAddition();
                long timeDifference = nowTimeInSeconds - entryTime;
                if(timeDifference > 60){
                    iteratorGroup.remove();
                    currentSize--;
                }
            }

        } finally {
            lock.writeLock().unlock(); // Освобождаем блокировку
        }
    }


    public void addStudentDTOtoCache(String studentId, StudentDTO studentDTO){
        lock.writeLock().lock(); // Захватываем блокировку для записи
        try {
            long currentTimeInSeconds = Instant.now().getEpochSecond(); //в секундах
            CacheEntity<StudentDTO> cacheEntity = new CacheEntity<>(studentDTO, currentTimeInSeconds);
            studentDTOcache.put(studentId, cacheEntity);
            currentSize++;

        }finally {
            lock.writeLock().unlock(); // Освобождаем блокировку
        }
        if(currentSize > maxSize){
            removeOldestStudentDTOfromCache();
            currentSize--;
        }
    }
    public Optional<StudentDTO> getStudentDTOfromCache(String studentId){
        if(studentDTOcache.containsKey(studentId)){
            StudentDTO studentDTO = studentDTOcache.get(studentId).getValue();
            studentDTO.setLastName("fromCache");
            return Optional.of(studentDTO);
        }else{
            return Optional.empty();
        }
    }
    public void removeStudentDTOfromCache(String studentId){
        if(currentSize > 0) {
            lock.writeLock().lock(); // Захватываем блокировку для записи
            try {
                studentDTOcache.remove(studentId);
                currentSize--;
            } finally {
                lock.writeLock().unlock(); // Освобождаем блокировку
            }
        }
    }
    public List<StudentDTO> getAllStudentCache(){
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for(CacheEntity<StudentDTO> cacheEntity : studentDTOcache.values()){  //set of values
            studentDTOList.add(cacheEntity.getValue());
        }
        return studentDTOList;
    }


    public void addGroupDTOtoCache(String groupId, GroupDTO groupDTO){
        lock.writeLock().lock(); // Захватываем блокировку для записи
        try {
            long currentTimeInSeconds = Instant.now().getEpochSecond(); //в секундах
            CacheEntity<GroupDTO> cacheEntity = new CacheEntity<>(groupDTO, currentTimeInSeconds);
            groupDTOcache.put(groupId, cacheEntity);
            currentSize++;
        } finally {
            lock.writeLock().unlock(); // Освобождаем блокировку
        }
        if(currentSize > maxSize){
            removeOldestGroupDTOfromCache();
            currentSize--;
        }
    }

    public Optional<GroupDTO> getGroupDTOfromCache(String groupId){
        if(groupDTOcache.containsKey(groupId)) {
            return Optional.of(groupDTOcache.get(groupId).getValue());
        }else{
            return Optional.empty();
        }
    }

    public void removeGroupDTOfromCache(String groupId){
        if(currentSize > 0) {
            lock.writeLock().lock(); // Захватываем блокировку для записи
            try {
                groupDTOcache.remove(groupId);
                currentSize--;
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    public List<String> getAllGroupCache(){
        List<String> groupList = new ArrayList<>();
        for(String groupId : groupDTOcache.keySet()){
            groupList.add(groupId);
        }
        return groupList;
    }

    private void removeOldestStudentDTOfromCache(){
        long oldestTime = Long.MAX_VALUE;
        String oldestKey = null;

        for(Map.Entry<String, CacheEntity<StudentDTO>> entry : studentDTOcache.entrySet()){
            long entryTime = entry.getValue().getTimeAddition();
            if(entryTime < oldestTime){
                oldestTime = entryTime;
                oldestKey = entry.getKey();
            }
        }

        if(oldestKey != null){
            studentDTOcache.remove(oldestKey);
        }
    }
    private void removeOldestGroupDTOfromCache(){
        long oldestTime = Long.MAX_VALUE;
        String oldestKey = null;

        for(Map.Entry<String, CacheEntity<GroupDTO>> entry : groupDTOcache.entrySet()){
            long entryTime = entry.getValue().getTimeAddition();
            if(entryTime < oldestTime){
                oldestTime = entryTime;
                oldestKey = entry.getKey();
            }
        }

        if(oldestKey != null){
            groupDTOcache.remove(oldestKey);
        }
    }
}
