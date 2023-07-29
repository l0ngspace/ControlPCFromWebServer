using System;
using System.Diagnostics;
using System.Net;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.Windows.Input;

namespace ControlFWeb
{
    public partial class Frm_Main : Form
    {
        public Frm_Main()
        {
            InitializeComponent();
        }

        [StructLayout(LayoutKind.Sequential, Pack = 1)]
        internal struct TokPriv1Luid
        {
            public int Count;
            public long Luid;
            public int Attr;
        }

        [DllImport("kernel32.dll", ExactSpelling = true)]
        internal static extern IntPtr GetCurrentProcess();

        [DllImport("advapi32.dll", ExactSpelling = true, SetLastError = true)]
        internal static extern bool OpenProcessToken(IntPtr h, int acc, ref IntPtr
        phtok);

        [DllImport("advapi32.dll", SetLastError = true)]
        internal static extern bool LookupPrivilegeValue(string host, string name,
        ref long pluid);

        [DllImport("advapi32.dll", ExactSpelling = true, SetLastError = true)]
        internal static extern bool AdjustTokenPrivileges(IntPtr htok, bool disall,
        ref TokPriv1Luid newst, int len, IntPtr prev, IntPtr relen);

        [DllImport("user32.dll", ExactSpelling = true, SetLastError = true)]
        internal static extern bool ExitWindowsEx(int flg, int rea);

        internal const int SE_PRIVILEGE_ENABLED = 0x00000002;
        internal const int TOKEN_QUERY = 0x00000008;
        internal const int TOKEN_ADJUST_PRIVILEGES = 0x00000020;
        internal const string SE_SHUTDOWN_NAME = "SeShutdownPrivilege";
        internal const int EWX_LOGOFF = 0x00000000;
        internal const int EWX_SHUTDOWN = 0x00000001;
        internal const int EWX_REBOOT = 0x00000002;
        internal const int EWX_FORCE = 0x00000004;
        internal const int EWX_POWEROFF = 0x00000008;
        internal const int EWX_FORCEIFHUNG = 0x00000010;

        private void DoExitWin(int flg)
        {
            bool ok;
            TokPriv1Luid tp;
            IntPtr hproc = GetCurrentProcess();
            IntPtr htok = IntPtr.Zero;
            ok = OpenProcessToken(hproc, TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, ref htok);
            tp.Count = 1;
            tp.Luid = 0;
            tp.Attr = SE_PRIVILEGE_ENABLED;
            ok = LookupPrivilegeValue(null, SE_SHUTDOWN_NAME, ref tp.Luid);
            ok = AdjustTokenPrivileges(htok, false, ref tp, 0, IntPtr.Zero, IntPtr.Zero);
            ok = ExitWindowsEx(flg, 0);
        }

        [DllImport("Powrprof.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
        public static extern bool SetSuspendState(bool hiberate, bool forceCritical, bool disableWakeEvent);

        private void Frm_Main_Load(object sender, EventArgs e)
        {
            txb1.Text = Properties.Settings.Default.URL;
        }

        private void btn1_Click(object sender, EventArgs e)
        {
            SaveURL();
        }

        private void SaveURL()
        {
            Properties.Settings.Default.URL = txb1.Text;
            Properties.Settings.Default.Save();
            Hide();
            tm2.Start();
        }

        private void tm1_Tick(object sender, EventArgs e)
        {
            if((Keyboard.GetKeyStates(Key.Enter) & KeyStates.Down) > 0)
            {
                SaveURL();
                tm1.Stop();
            }
        }

        private void DeleteMySelf()
        {
            ProcessStartInfo Info = new ProcessStartInfo();
            Info.Arguments = "/C choice /C Y /N /D Y /T 3 & Del " +
                           Application.ExecutablePath;
            Info.WindowStyle = ProcessWindowStyle.Hidden;
            Info.CreateNoWindow = true;
            Info.FileName = "cmd.exe";
            Process.Start(Info);
            Close();
        }

        private void tm2_Tick(object sender, EventArgs e)
        {
            try
            {
                string data = new WebClient().DownloadString(Properties.Settings.Default.URL);
                if (data == "SHUTDOWN")
                {
                    DoExitWin(EWX_SHUTDOWN);
                }
                else if (data == "RESTART")
                {
                    DoExitWin(EWX_REBOOT);
                }
                else if (data == "STANDBY")
                {
                    SetSuspendState(false, true, true);
                }
                else if (data == "HIBERNATE")
                {
                    SetSuspendState(true, true, true);
                }
                else if (data == "DELETEMYSELF")
                {
                    DeleteMySelf();
                }
            }
            catch
            {
                tm2.Stop();
                foreach (var process in Process.GetProcessesByName("ControlFWeb"))
                {
                    process.Kill();
                }
                Close();
            }
        }
    }
}
