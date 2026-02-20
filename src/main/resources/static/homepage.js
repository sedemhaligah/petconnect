import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact>
                    <Homepage />
                </Route>
                {/* Hier können weitere Routen für andere Seiten hinzugefügt werden */}
            </Switch>
        </Router>
    );
};

const Homepage = () => {
    return (
        <div>
            {/* Dein Homepage-Inhalt hier */}
        </div>
    );
};

export default App;
