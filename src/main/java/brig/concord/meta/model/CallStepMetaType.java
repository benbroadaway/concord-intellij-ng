package brig.concord.meta.model;

import brig.concord.meta.ConcordAnyMapMetaType;
import org.jetbrains.yaml.meta.model.YamlMetaType;
import org.jetbrains.yaml.meta.model.YamlStringType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CallStepMetaType extends StepMetaType {

    private static final CallStepMetaType INSTANCE = new CallStepMetaType();

    public static CallStepMetaType getInstance() {
        return INSTANCE;
    }

    private static final Map<String, Supplier<YamlMetaType>> features = Map.of(
            "call", CallMetaType::getInstance,
            "name", YamlStringType::getInstance,
            "in", InParamsMetaType::getInstance,
            "out", CallOutParamsMetaType::getInstance,
            "meta", ConcordAnyMapMetaType::getInstance,
//            "loop", ,
            "retry", RetryMetaType::getInstance,
            "error", StepsMetaType::getInstance
    );

    protected CallStepMetaType() {
        super("Call", "call", Set.of("call"));
    }

    @Override
    public Map<String, Supplier<YamlMetaType>> getFeatures() {
        return features;
    }
}
