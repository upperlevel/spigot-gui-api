package xyz.upperlevel.spigot.gui.config.action.actions;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.upperlevel.spigot.gui.SlimyGuis;
import xyz.upperlevel.spigot.gui.config.action.Action;
import xyz.upperlevel.spigot.gui.config.action.BaseActionType;
import xyz.upperlevel.spigot.gui.config.action.Parser;
import xyz.upperlevel.spigot.gui.config.placeholders.PlaceholderValue;
import xyz.upperlevel.spigot.gui.hotbar.Hotbar;
import xyz.upperlevel.spigot.gui.hotbar.HotbarManager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HotbarGiveAction extends Action<HotbarGiveAction> {
    public static final HotbarGiveActionType TYPE = new HotbarGiveActionType();
    @Getter
    private final PlaceholderValue<String> id;
    @Getter
    private final List<Action> actions;
    @Getter
    private final List<Action> fail;

    public HotbarGiveAction(PlaceholderValue<String> id, List<Action> actions, List<Action> fail) {
        super(TYPE);
        this.id = id;
        this.actions = actions;
        this.fail = fail;
    }

    @Override
    public void run(Player player) {
        final String pid = id.get(player);
        final Hotbar hotbar = HotbarManager.get(pid);
        if (hotbar == null) {
            SlimyGuis.logger().severe("Cannot find hotbar \"" + pid + "\"");
            return;
        }
        hotbar.give(player);
    }


    public static class HotbarGiveActionType extends BaseActionType<HotbarGiveAction> {

        public HotbarGiveActionType() {
            super("hotbar-give");
            setParameters(
                    Parameter.of("id", Parser.strValue(), true),
                    Parameter.of("actions", Parser.actionsValue(), Collections.emptyList(), false),
                    Parameter.of("fail", Parser.actionsValue(), Collections.emptyList(), false)
            );
        }

        @Override
        @SuppressWarnings("unchecked")//:(
        public HotbarGiveAction create(Map<String, Object> pars) {
            return new HotbarGiveAction(
                    PlaceholderValue.strValue((String) pars.get("id")),
                    (List<Action>) pars.get("actions"),
                    (List<Action>) pars.get("fail")
            );
        }

        @Override
        public Map<String, Object> read(HotbarGiveAction action) {
            return ImmutableMap.of(
                    "id", action.id.toString(),
                    "action", action.actions,
                    "fail", action.fail
            );
        }
    }
}
