package uk.ac.gla.teamL.execution.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ProgramRunner;
import org.jetbrains.annotations.Nullable;

/**
 * User: nishad
 * Date: 10/03/15
 * Time: 13:26
 */
public class EBNFRunProfileState implements RunProfileState {
    EBNFRunConfiguration configuration;

    public EBNFRunProfileState(EBNFRunConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Starts the process.
     *
     * @param executor the executor used to start up the process.
     * @param runner   the program runner used to start up the process.
     * @return the result (normally an instance of {@link com.intellij.execution.DefaultExecutionResult}), containing a process handler
     * and a console attached to it.
     * @throws com.intellij.execution.ExecutionException if the execution has failed.
     */
    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, ProgramRunner runner) throws ExecutionException {
        return null; // TODO NEVER. https://confluence.jetbrains.com/display/IDEADEV/Run+Configurations
    }
}
