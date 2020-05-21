/*
* Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary
import Sketch

class ViewController: UIViewController {
    
    @IBOutlet weak var sketchView: SketchView!
    @IBOutlet weak var resultLabel: UILabel!
    
    private var tfDigitClassifier: TFDigitClassifier?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
    }

    @IBAction func tapClear(_ sender: Any) {
    }
    
}
