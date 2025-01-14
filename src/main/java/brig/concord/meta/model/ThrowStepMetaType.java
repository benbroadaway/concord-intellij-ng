package brig.concord.meta.model;

import org.jetbrains.yaml.meta.model.YamlMetaType;
import org.jetbrains.yaml.meta.model.YamlStringType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ThrowStepMetaType extends StepMetaType {

    private static final ThrowStepMetaType INSTANCE = new ThrowStepMetaType();

    public static ThrowStepMetaType getInstance() {
        return INSTANCE;
    }

    private static final Map<String, Supplier<YamlMetaType>> features = Map.of(
            "throw", YamlStringType::getInstance);

    protected ThrowStepMetaType() {
        super("Throw", "throw", Set.of("throw"));
    }

    @Override
    public Map<String, Supplier<YamlMetaType>> getFeatures() {
        return features;
    }
}
