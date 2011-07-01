/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.core.constructionheuristic.greedy;

import java.util.Map;

import org.drools.FactHandle;
import org.drools.WorkingMemory;
import org.drools.planner.core.domain.meta.PlanningVariableDescriptor;
import org.drools.planner.core.phase.AbstractSolverPhaseScope;
import org.drools.planner.core.phase.step.AbstractStepScope;

public class GreedyStepScope extends AbstractStepScope {

    private final GreedySolverPhaseScope greedySolverPhaseScope;

    private Object planningEntity;

    private Map<PlanningVariableDescriptor, Object> variableToValueMap;

    public GreedyStepScope(GreedySolverPhaseScope greedySolverPhaseScope) {
        this.greedySolverPhaseScope = greedySolverPhaseScope;
    }

    public GreedySolverPhaseScope getGreedySolverPhaseScope() {
        return greedySolverPhaseScope;
    }

    @Override
    public AbstractSolverPhaseScope getSolverPhaseScope() {
        return greedySolverPhaseScope;
    }

    public Object getPlanningEntity() {
        return planningEntity;
    }

    public void setPlanningEntity(Object planningEntity) {
        this.planningEntity = planningEntity;
    }

    public void setVariableToValueMap(Map<PlanningVariableDescriptor, Object> variableToValueMap) {
        this.variableToValueMap = variableToValueMap;
    }

    // ************************************************************************
    // Calculated methods
    // ************************************************************************

    public void doStep() {
        WorkingMemory workingMemory = greedySolverPhaseScope.getWorkingMemory();
        FactHandle factHandle = workingMemory.getFactHandle(planningEntity);
        for (Map.Entry<PlanningVariableDescriptor, Object> entry : variableToValueMap.entrySet()) {
            PlanningVariableDescriptor planningVariableDescriptor = entry.getKey();
            Object value = entry.getValue();
            planningVariableDescriptor.setValue(planningEntity, value);
        }
        workingMemory.update(factHandle, planningEntity);
        // there is no need to recalculate the score, but we still need to set it
        greedySolverPhaseScope.getWorkingSolution().setScore(score);
    }

}
