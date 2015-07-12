package zairus.iskallminimobs.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MMEntityRegistry
{
	private static List<Class<? extends Entity>> registeredEntities = new ArrayList<Class<? extends Entity>>();
	
	public static int getNextEntityId()
	{
		return registeredEntities.size();
	}
	
	public static int registerEntity(Class<? extends Entity> entityClass, String entityId, Object mod, int trackingTime, int updateFrecuency, boolean sendVelocityUpdates)
	{
		int id = getNextEntityId();
		
		registeredEntities.add(entityClass);
		EntityRegistry.registerModEntity(entityClass, entityId, id, mod, trackingTime, updateFrecuency, sendVelocityUpdates);
		
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public static int registerEntity(Class<? extends Entity> entityClass, String entityId, Object mod, int trackingTime, int updateFrecuency, boolean sendVelocityUpdates, int eggBackground, int eggForeground)
	{
		int id = registerEntity(
				entityClass,
				entityId,
				mod,
				trackingTime,
				updateFrecuency,
				sendVelocityUpdates);
		
		EntityList.IDtoClassMapping.put(id, entityClass);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, eggBackground, eggForeground));
		
		return id;
	}
}
