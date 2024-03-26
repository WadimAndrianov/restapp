package ru.and.restapp.model.Cache;

import org.springframework.stereotype.Component;
import ru.and.restapp.model.GroupDTO;
import ru.and.restapp.model.StudentDTO;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class CacheManager {
    private final ConcurrentHashMap<String, CacheEntity<StudentDTO>> studentDTOcache =  new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheEntity<GroupDTO>> groupDTOcache =  new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    public CacheManager() {
        // Запускаем периодическую задачу для удаления старых объектов из кэша каждые две минуты
        executorService.scheduleAtFixedRate(this::clearExpiredCache, 0, 2, TimeUnit.MINUTES);
        //             scheduleWithFixedDelay()
    }
    private void clearExpiredCache() {
        lock.writeLock().lock(); // Захватываем блокировку для записи
        try {
            long nowTimeInSeconds = Instant.now().getEpochSecond(); //в секундах
            Iterator<Map.Entry<String, CacheEntity<StudentDTO>>> iterator = studentDTOcache.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, CacheEntity<StudentDTO>> entryStudent = iterator.next();
                long entryTime = entryStudent.getValue().timeAddition();
                long timeDifference = nowTimeInSeconds - entryTime;
                if(timeDifference > 120){
                    iterator.remove();
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
        }finally {
            lock.writeLock().unlock(); // Освобождаем блокировку
        }
    }
    public Optional<StudentDTO> getStudentDTOfromCache(String studentId){
        if(studentDTOcache.containsKey(studentId)){
        return Optional.of(studentDTOcache.get(studentId).getValue());
        }else{
            return Optional.empty();
        }
    }
    public void removeStudentDTOfromCache(String studentId){
        lock.writeLock().lock(); // Захватываем блокировку для записи
        try {
            studentDTOcache.remove(studentId);
        }finally {
            lock.writeLock().unlock(); // Освобождаем блокировку
        }
    }

    public void addGroupDTOtoCache(String groupId, GroupDTO groupDTO){
        long currentTimeInSeconds = Instant.now().getEpochSecond(); //в секундах
        CacheEntity<GroupDTO> cacheEntity = new CacheEntity<>(groupDTO, currentTimeInSeconds);
        groupDTOcache.put(groupId, cacheEntity);
    }

    public Optional<GroupDTO> getGroupDTOfromCache(String groupId){
        if(groupDTOcache.containsKey(groupId)) {
            return Optional.of(groupDTOcache.get(groupId).getValue());
        }else{
            return Optional.empty();
        }
    }

    public void removeGroupDTOfromCache(String groupId){
        groupDTOcache.remove(groupId);
    }
}
