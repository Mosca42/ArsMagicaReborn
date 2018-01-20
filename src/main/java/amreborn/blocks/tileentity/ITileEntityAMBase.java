package amreborn.blocks.tileentity;

public interface ITileEntityAMBase {

    public void markForUpdate();
    public boolean needsUpdate();
    public void clean();
}
