package naga.framework.operation;

import naga.util.async.AsyncFunction;

/**
 * @author Bruno Salmon
 */
public class ChainedActionOperationExecutor extends ChainedOperationExecutor {

    private final OperationActionRegistry registry;

    public ChainedActionOperationExecutor(OperationActionRegistry registry, AsyncFunction nextOperationExecutor) {
        super(nextOperationExecutor);
        this.registry = registry;
    }

    public OperationActionRegistry getRegistry() {
        return registry;
    }

}
