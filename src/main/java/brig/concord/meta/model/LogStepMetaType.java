package brig.concord.meta.model;

import org.jetbrains.yaml.meta.model.YamlMetaType;
import org.jetbrains.yaml.meta.model.YamlStringType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class LogStepMetaType extends StepMetaType {

    private static final LogStepMetaType INSTANCE = new LogStepMetaType();

    public static LogStepMetaType getInstance() {
        return INSTANCE;
    }

    private static final Map<String, Supplier<YamlMetaType>> features = Map.of(
            "log", YamlStringType::getInstance);

    protected LogStepMetaType() {
        super("Log", "log", Set.of("log"));
    }

    @Override
    public Map<String, Supplier<YamlMetaType>> getFeatures() {
        return features;
    }
}
