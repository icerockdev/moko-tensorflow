/*
* Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary
import Sketch

class ViewController: UIViewController, SketchViewDelegate {
    
    @IBOutlet weak var sketchView: SketchView!
    @IBOutlet weak var resultLabel: UILabel!
    
    private var interpreter: TensorflowInterpreter?
    private var tfDigitClassifier: TFDigitClassifier?
    
    private var isInterpreterInited: Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        // Setup sketch view.
        sketchView.lineWidth = 30
        sketchView.backgroundColor = UIColor.black
        sketchView.lineColor = UIColor.white
        sketchView.sketchViewDelegate = self
        
        let options: TensorflowInterpreterOptions = TensorflowInterpreterOptions(numThreads: 2)
        let modelFileRes: ResourcesFileResource = ResHolder().getDigitsClassifierModel()
        
        interpreter = TensorflowInterpreter(fileResource: modelFileRes, options: options)
        tfDigitClassifier = TFDigitClassifier(interpreter: interpreter!)
        
        tfDigitClassifier?.initialize {
            self.isInterpreterInited = true
        }
    }
    
    deinit {
        interpreter?.close()
    }

    @IBAction func tapClear(_ sender: Any) {
        sketchView.clear()
        resultLabel.text = "Please draw a digit."
    }
    
    /// Callback executed every time there is a new drawing
    func drawView(_ view: SketchView, didEndDrawUsingTool tool: AnyObject) {
      classifyDrawing()
    }
    
    private func classifyDrawing() {
        guard let tfDigitClassifier = self.tfDigitClassifier else { return }

        // Capture drawing to RGB file.
        UIGraphicsBeginImageContext(sketchView.frame.size)
        sketchView.layer.render(in: UIGraphicsGetCurrentContext()!)
        let drawing = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext();
        
        guard drawing != nil else {
          resultLabel.text = "Invalid drawing."
          return
        }
        
        let rgbData = drawing!.scaledData(with: CGSize(width: Int(tfDigitClassifier.inputImageWidth), height: Int(tfDigitClassifier.inputImageHeight)))
        
        
        tfDigitClassifier.classifyAsync(inputData: rgbData) { (String) in
            print(String)
        }
    }
    
}
