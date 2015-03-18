package org.matrix.jsynapse.dao.redis;

import org.matrix.jsynapse.dao.RoomRepository;
import org.matrix.jsynapse.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class RoomRepositoryImpl implements RoomRepository {
    private final RedisTemplate redisTemplate;
    private final String ROOM_NAMESPACE = "ROOM";
    private final String ROOM_KEY = "room:%s:id";

    @Autowired
    public RoomRepositoryImpl(final RedisTemplate template) {
        redisTemplate = template;
    }
    @Override
    public Room createRoom(Room room) {
        HashOperations ops = redisTemplate.opsForHash();
        if (ops.hasKey(ROOM_NAMESPACE, room.getAliasName())) {
            return null;
        }
        ops.put(ROOM_NAMESPACE, String.format(ROOM_KEY, room.getAliasName()), room.getName());
        return room;
    }

    @Override
    public Room getRoom(String alias) {
        String name = (String) redisTemplate.opsForHash().get(ROOM_NAMESPACE, String.format(ROOM_KEY, alias));
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return new Room(alias, name);
    }
}
