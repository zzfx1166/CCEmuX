package net.clgd.ccemux.plugins.builtin;

import com.iung.ccwasm.api.Api1;
import net.clgd.ccemux.api.emulation.EmulatedComputer;
import net.clgd.ccemux.api.emulation.Emulator;
import net.clgd.ccemux.api.plugins.Plugin;
import net.clgd.ccemux.api.plugins.PluginManager;
import net.clgd.ccemux.api.plugins.hooks.ComputerCreated;
import com.iung.ccwasm.api.IComputerSystem;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.google.auto.service.AutoService;
import dan200.computercraft.api.filesystem.Mount;
import dan200.computercraft.core.filesystem.FileMount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutoService(Plugin.class)
public class CCWASMPlugin extends Plugin {

	private static final Logger LOGGER = LoggerFactory.getLogger(CCWASMPlugin.class);

	@Nonnull
	@Override
	public String getName() {
		return "CCWASM";
	}

	@Nonnull
	@Override
	public String getDescription() {
		return "Adds WebAssembly (WASM) support to ComputerCraft in CCEmuX, allowing computers to load and execute WASM modules.";
	}

	@Nonnull
	@Override
	public Optional<String> getVersion() {
		return Optional.of("1.0.0");
	}

	@Nonnull
	@Override
	public Collection<String> getAuthors() {
		return Collections.singletonList("iung");
	}

	@Nonnull
	@Override
	public Optional<String> getWebsite() {
		return Optional.of("https://github.com/wefcdse/ccwasm");
	}

	@Override
	public void setup(@Nonnull PluginManager manager) {
		// Register hook to add WASM API to each computer
		registerHook((ComputerCreated) (emu, computer) -> {
			// Create a wrapper that implements IComputerSystem using the EmulatedComputer
			com.iung.ccwasm.api.IComputerSystem computerSystem = new com.iung.ccwasm.api.IComputerSystem() {
				@Override
				public void mount(@Nonnull String location, @Nonnull Mount mount) {
					try {
						// ComputerCraft's FileSystem.mount requires a path, old location, and mount
						computer.getEnvironment().getFileSystem().mount(location, location, mount);
					} catch (dan200.computercraft.core.filesystem.FileSystemException e) {
						// Log the error but don't crash the application
						LOGGER.error("Failed to mount filesystem at {}", location, e);
					}
				}

				@Override
				public void unmount(@Nonnull String location) {
					computer.getEnvironment().getFileSystem().unmount(location);
				}

				// Note: This is a simplified implementation. In a full implementation,
				// we might need to provide more methods from IComputerSystem
			};

			computer.addApi(new Api1(computerSystem));
		});
	}

	/**
	 * Simplified interface to mimic ComputerCraft's IComputerSystem
	 */
	public interface IComputerSystem {
		void mount(@Nonnull String location, @Nonnull dan200.computercraft.api.filesystem.Mount mount);
		void unmount(@Nonnull String location);
	}
}