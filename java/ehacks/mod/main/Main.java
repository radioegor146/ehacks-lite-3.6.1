package ehacks.mod.main;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Wrapper;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

@Mod(modid = "EHacks Lite", name = "EHacks Lite", version = "3.6.1")
public class Main {

    @Mod.Instance(value = "EHacks Lite")
    public static Main INSTANCE;
    
    public static String version = "3.6.1";

    public static boolean isInjected;

    public static FMLModContainer mainContainer;
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event == null) {
            isInjected = true;
        }
        try {
            INSTANCE = this;
            ModuleManagement.instance();
            FMLCommonHandler.instance().bus().register((Object) new Events());
            MinecraftForge.EVENT_BUS.register((Object) new Events());
            ReflectionHelper.setPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), (Object) ((Object) new TimerUtils(20.0f)), (String[]) new String[]{Mappings.timer});
            new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehackslite").mkdirs();
            FMLCommonHandler.instance().bus().register((Object)this);
            MinecraftForge.EVENT_BUS.register((Object)this);
            XRayBlock.init();
            XRay.displayListid = GL11.glGenLists((int) 5) + 3;
            ConfigurationManager.instance().initConfigs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main(int sig) {
        if (sig == 1337) {
            init(null);
        }
    }

    public Main() {

    }
}
