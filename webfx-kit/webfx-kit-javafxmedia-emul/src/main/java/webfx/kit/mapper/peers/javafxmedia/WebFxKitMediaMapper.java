package webfx.kit.mapper.peers.javafxmedia;

import javafx.scene.media.Media;
import webfx.kit.mapper.peers.javafxmedia.spi.WebFxKitMediaMapperProvider;
import webfx.platform.shared.util.serviceloader.SingleServiceProvider;

import java.util.ServiceLoader;

/**
 * @author Bruno Salmon
 */
public class WebFxKitMediaMapper {

    private static WebFxKitMediaMapperProvider getProvider() {
        return SingleServiceProvider.getProvider(WebFxKitMediaMapperProvider.class, () -> ServiceLoader.load(WebFxKitMediaMapperProvider.class));
    }

    public static MediaPlayerPeer createMediaPlayerPeer(Media media) {
        return getProvider().createMediaPlayerPeer(media);
    }
}
