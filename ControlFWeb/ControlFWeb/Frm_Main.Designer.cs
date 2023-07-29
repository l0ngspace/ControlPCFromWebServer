namespace ControlFWeb
{
    partial class Frm_Main
    {
        private System.ComponentModel.IContainer components = null;
		
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form

        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.btn1 = new System.Windows.Forms.Button();
            this.txb1 = new System.Windows.Forms.TextBox();
            this.lbl1 = new System.Windows.Forms.Label();
            this.tm1 = new System.Windows.Forms.Timer(this.components);
            this.tm2 = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // btn1
            // 
            this.btn1.Location = new System.Drawing.Point(237, 51);
            this.btn1.Name = "btn1";
            this.btn1.Size = new System.Drawing.Size(149, 30);
            this.btn1.TabIndex = 0;
            this.btn1.Text = "Save And Listen From URL";
            this.btn1.UseVisualStyleBackColor = true;
            this.btn1.Click += new System.EventHandler(this.btn1_Click);
            // 
            // txb1
            // 
            this.txb1.Location = new System.Drawing.Point(12, 23);
            this.txb1.Name = "txb1";
            this.txb1.Size = new System.Drawing.Size(374, 20);
            this.txb1.TabIndex = 1;
            // 
            // lbl1
            // 
            this.lbl1.AutoSize = true;
            this.lbl1.Location = new System.Drawing.Point(12, 6);
            this.lbl1.Name = "lbl1";
            this.lbl1.Size = new System.Drawing.Size(32, 13);
            this.lbl1.TabIndex = 2;
            this.lbl1.Text = "URL:";
            // 
            // tm1
            // 
            this.tm1.Enabled = true;
            this.tm1.Tick += new System.EventHandler(this.tm1_Tick);
            // 
            // tm2
            // 
            this.tm2.Tick += new System.EventHandler(this.tm2_Tick);
            // 
            // Frm_Main
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(398, 86);
            this.Controls.Add(this.lbl1);
            this.Controls.Add(this.txb1);
            this.Controls.Add(this.btn1);
            this.MaximizeBox = false;
            this.Name = "Frm_Main";
            this.ShowIcon = false;
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "ControlFWeb - Main";
            this.TopMost = true;
            this.Load += new System.EventHandler(this.Frm_Main_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn1;
        private System.Windows.Forms.TextBox txb1;
        private System.Windows.Forms.Label lbl1;
        private System.Windows.Forms.Timer tm1;
        private System.Windows.Forms.Timer tm2;
    }
}

