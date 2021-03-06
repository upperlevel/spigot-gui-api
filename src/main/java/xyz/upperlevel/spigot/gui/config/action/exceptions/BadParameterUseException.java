package xyz.upperlevel.spigot.gui.config.action.exceptions;

/**
 * This is called when a multiple required parameter action is being called using a single parameter
 */
public class BadParameterUseException extends IllegalParametersException {

    public BadParameterUseException() {
        super(null, "Cannot initialize a multiple-parameter action with only one parameter!");
    }
}
