package com.iung.ccwasm.api;

import javax.annotation.Nonnull;
import dan200.computercraft.api.filesystem.Mount;

/**
 * Interface to provide ComputerCraft-like filesystem operations for WASM modules.
 * This interface is used by Api1 to mount and unmount filesystems in the computer.
 */
public interface IComputerSystem {
    /**
     * Mount a filesystem at the given location.
     * @param location The path to mount at (e.g. "/wasm")
     * @param mount The mount to attach
     */
    void mount(@Nonnull String location, @Nonnull Mount mount);

    /**
     * Unmount a filesystem at the given location.
     * @param location The path to unmount
     */
    void unmount(@Nonnull String location);
}