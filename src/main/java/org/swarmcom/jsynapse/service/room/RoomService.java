/*
 * (C) Copyright 2015 eZuce Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
package org.swarmcom.jsynapse.service.room;

import org.swarmcom.jsynapse.domain.Room;

public interface RoomService {
    Room createRoom(Room room);

    Room findRoomByAlias(String alias);

    void deleteAlias(String roomId, String alias);

    Room findRoomById(String roomId);

    Room saveTopic(String roomId, String topic);

    Room saveName(String roomId, String name);
}
