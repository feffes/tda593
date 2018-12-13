package mdsd.controller;

import mdsd.model.IProcedure;
import mdsd.view.IRewardView;

public interface IRewardControlller {
    void updateProcendure(IProcedure procedure);

    void addRewardView(IRewardView view);

    void addProcedure(IProcedure procedure);

    void startTimer();
}
